package uk.ac.swansea.autograder.api.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.api.controllers.dto.ProblemDto;
import uk.ac.swansea.autograder.api.entities.Problem;
import uk.ac.swansea.autograder.api.repositories.ProblemRepository;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
public class ProblemService {
    private final ProblemRepository problemRepository;

    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public Problem createProblem(ProblemDto problemDto) {
        Problem problem = new Problem();
        problem.setTitle(problemDto.getTitle());
        problem.setDescription(problemDto.getDescription());
        problem.setStatus(Problem.Status.ACTIVE);
        problem.setUserId(problemDto.getUserId());
        return problemRepository.save(problem);
    }

    public Problem getProblem(Long id) throws ResourceNotFoundException {
        return problemRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public List<Problem> getProblemsByuserId(Long id, Pageable pageable) {
        return problemRepository.findAllByUserId(id, pageable);
    }

    public List<Problem> getProblems(Pageable pageable) {
        return problemRepository.findAll(pageable).toList();
    }

    public Problem updateProblem(Long id, ProblemDto problemDto) throws ResourceNotFoundException {
        Problem problem = getProblem(id);
        problem.setTitle(problemDto.getTitle());
        problem.setDescription(problemDto.getDescription());
        problem.setStatus(problemDto.getStatus());
        return problemRepository.save(problem);
    }
}
