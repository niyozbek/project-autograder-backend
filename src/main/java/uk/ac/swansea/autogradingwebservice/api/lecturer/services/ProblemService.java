package uk.ac.swansea.autogradingwebservice.api.lecturer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autogradingwebservice.api.lecturer.controllers.dto.ProblemDto;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.Problem;
import uk.ac.swansea.autogradingwebservice.api.lecturer.repositories.ProblemRepository;
import uk.ac.swansea.autogradingwebservice.exceptions.ResourceNotFoundException;

@Service
public class ProblemService {
    @Autowired
    private ProblemRepository problemRepository;

    public Problem createProblem(ProblemDto problemDto) {
        Problem problem = new Problem();
        problem.setDescription(problemDto.getDescription());
        problem.setStatus(1);
        problem.setLecturer_id(problemDto.getLecturer_id());
        return problemRepository.save(problem);
    }

    public Problem getProblem(Long id) throws ResourceNotFoundException {
        return problemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
    }
}
