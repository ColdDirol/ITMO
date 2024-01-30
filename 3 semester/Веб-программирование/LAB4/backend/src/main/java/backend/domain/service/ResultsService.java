package backend.domain.service;

import backend.domain.dto.ResultCreationRequest;
import backend.domain.dto.ResultDto;
import backend.domain.dto.ResultResponse;
import backend.domain.entity.Results;
import backend.domain.entity.Users;
import backend.repository.ResultsRepository;
import backend.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultsService {
    private final UsersRepository usersRepository;
    private final ResultsRepository resultsRepository;

    public ResultResponse add(ResultCreationRequest request, String userEmail) throws ChangeSetPersister.NotFoundException {
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Double x = request.getX();
        Double y = request.getY();
        Double r = request.getR();
        Boolean isHit = ResultUtil.check(x, y, r);

        var result = Results.builder()
                .x(x)
                .y(y)
                .r(r)
                .result(isHit)
                .user(user)
                .build();
        System.out.println(result);
        resultsRepository.save(result);
        return ResultResponse.builder()
                .result(isHit)
                .build();
    }

    public List<ResultDto> getAllByUserEmail(String userEmail) {
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        List<Results> resultsList = resultsRepository.findAllByUser_Id(user.getId());

        return ResultDto.resultsListToResultDtoList(resultsList);
    }

    @Transactional
    public Boolean deleteAllByUserEmail(String userEmail) {
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        resultsRepository.deleteResultsByUser_Id(user.getId());
        return true;

    }
}
