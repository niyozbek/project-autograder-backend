package uk.ac.swansea.autogradingwebservice.api.lecturer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autogradingwebservice.api.lecturer.controllers.dto.ProblemDto;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.Problem;
import uk.ac.swansea.autogradingwebservice.api.lecturer.repositories.ProblemRepository;
import uk.ac.swansea.autogradingwebservice.exceptions.BadRequestException;
import uk.ac.swansea.autogradingwebservice.exceptions.ResourceNotFoundException;

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
        problem.setStatus(1);
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

    public List<Problem> getProblemsByLecturerId(Long id) {
        return problemRepository.findAllByLecturerId(id);
    }

    public List<Problem> getProblems() {
        return (List<Problem>) problemRepository.findAll();
    }

}
