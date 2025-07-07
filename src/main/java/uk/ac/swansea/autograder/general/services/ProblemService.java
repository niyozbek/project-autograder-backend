package uk.ac.swansea.autograder.general.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.api.lecturer.controllers.dto.ProblemDto;
import uk.ac.swansea.autograder.general.entities.Problem;
import uk.ac.swansea.autograder.general.repositories.ProblemRepository;
import uk.ac.swansea.autograder.exceptions.BadRequestException;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Objects;

@Service
public class ProblemService {
    @Autowired
    private ProblemRepository problemRepository;

    public Problem createProblem(ProblemDto problemDto) {
        Problem problem = new Problem();
        problem.setTitle(problemDto.getTitle());
        problem.setDescription(problemDto.getDescription());
        problem.setStatus(Problem.Status.ACTIVE);
        problem.setLecturerId(problemDto.getLecturerId());
        return problemRepository.save(problem);
    }

    public Problem getProblemByLecturerId(Long id, Long lecturerId) throws ResourceNotFoundException,
            BadRequestException {
        Problem problem = getProblem(id);
        if (!Objects.equals(problem.getLecturerId(), lecturerId)) {
            throw new BadRequestException();
        }
        return problem;
    }

    public Problem getProblem(Long id) throws ResourceNotFoundException {
        return problemRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public List<Problem> getProblemsByLecturerId(Long id, Pageable pageable) {
        return problemRepository.findAllByLecturerId(id, pageable);
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
