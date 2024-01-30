package backend.controller;

import backend.domain.dto.ResultCreationRequest;
import backend.domain.dto.ResultDto;
import backend.domain.dto.ResultResponse;
import backend.domain.service.ResultsService;
import backend.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/result")
@RequiredArgsConstructor
@CrossOrigin
public class ResultController {
    private final ResultsService resultsService;
    private final JWTService jwtService;

    @PostMapping
    public ResponseEntity<ResultResponse> register(
            @RequestBody ResultCreationRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            return ResponseEntity.ok(resultsService.add
                    (
                        request,
                        jwtService.extractUsername(jwtService.extractJwtTokenFromHeader(authHeader))
                    )
            );
        } catch (UsernameNotFoundException | ChangeSetPersister.NotFoundException e) {
            return (ResponseEntity<ResultResponse>) ResponseEntity.notFound();
        }
    }

    @GetMapping
    public ResponseEntity<List<ResultDto>> getAllByUserEmail(
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            return ResponseEntity.ok(resultsService.getAllByUserEmail
                    (
                            jwtService.extractUsername(jwtService.extractJwtTokenFromHeader(authHeader))
                    )
            );
        } catch (UsernameNotFoundException e) {
            return (ResponseEntity<List<ResultDto>>) ResponseEntity.notFound();
        }
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAllByUserEmail(
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            return ResponseEntity.ok(resultsService.deleteAllByUserEmail
                    (
                            jwtService.extractUsername(jwtService.extractJwtTokenFromHeader(authHeader))
                    )
            );
        } catch (UsernameNotFoundException e) {
            return (ResponseEntity<Boolean>) ResponseEntity.notFound();
        }
    }

}
