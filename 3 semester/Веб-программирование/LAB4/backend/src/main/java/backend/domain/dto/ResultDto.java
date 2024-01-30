package backend.domain.dto;

import backend.domain.entity.Results;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {
    private Double x;
    private Double y;
    private Double r;
    private Boolean result;

    public static List<ResultDto> resultsListToResultDtoList(List<Results> resultsList) {
        return resultsList.stream()
                .map(results -> ResultDto.builder()
                        .x(results.getX())
                        .y(results.getY())
                        .r(results.getR())
                        .result(results.getResult())
                        .build())
                .collect(Collectors.toList());
    }
}
