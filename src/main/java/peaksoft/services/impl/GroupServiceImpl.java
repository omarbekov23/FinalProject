package peaksoft.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.dto.GroupDto;
import peaksoft.dto.mapper.GroupMapper;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.Group;
import peaksoft.models.Response;
import peaksoft.repositorys.GroupRepository;
import peaksoft.services.CourseService;
import peaksoft.services.GroupService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final CourseService service;
    private final GroupMapper mapper;

    @Override
    public Response saveGroup(GroupDto group, Long id) {
        String groupName = group.getGroupName();
        checkGroupName(groupName);
        Group group1 = mapper.create(group);
        group1.setCourse(service.getById(id));
        Group saveGroup = groupRepository.save(group1);
        log.info("Group with groupName = {} has sucessfully saved to database", saveGroup.getGroupName());
        return Response.builder()
                .httpStatus(CREATED)
                .message(String.format("Group with groupName = %s successfully registered",
                        saveGroup.getGroupName()))
                .build();
    }

    private void checkGroupName(String groupName) {
        boolean exists = groupRepository.existsByGroupName(groupName);
        if (exists) {
            log.warn("group with groupName = {} already exists", groupName);
            throw new BadRequestException(
                    "group with groupName = " + groupName + " already exists"
            );
        }
    }

    @Override
    public Response deleteById(Long id) {
        groupRepository.deleteById(id);
        log.info("Group with id = {} has successfully deleted", id);
        String message = String.format("Group with id = %s has successfully deleted", id);
        return Response.builder()
                .httpStatus(OK)
                .message(message)
                .build();
    }

    @Override
    public Group getById(Long id) {
        Group course = groupRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("group with id = {} does not exists", id);
                    throw new NotFoundException(
                            String.format("group with id = %s does not exists", id)
                    );
                });
        log.info("founded group with id = {}", id);
        return course;
    }

    @Override
    public List<Group> findAllGroup() {
        List<Group> allGroup = groupRepository.findAll();
        log.info("founded {} group", allGroup.size());
        return allGroup;
    }

    @Override
    @Transactional
    public Response updateById(Long id, GroupDto newGroup) {
        Group group = groupRepository.getById(id);

        String groupName = group.getGroupName();
        String newGroupName = newGroup.getGroupName();
        if (!Objects.equals(groupName, newGroupName)) {
            group.setGroupName(newGroupName);
            log.info("Group with id = {} changed name from {} to {}",
                    id, groupName, newGroupName);
        }

        String dateOfStart = group.getDateOfStart();
        String newDateOfStart = newGroup.getGroupName();
        if (!Objects.equals(dateOfStart, newDateOfStart)) {
            group.setDateOfStart(newDateOfStart);
            log.info("Group with id = {} changed name from {} to {}",
                    id, dateOfStart, newDateOfStart);
        }
        String dateOfFinish = group.getDateOfFinish();
        String newDateOfFinish = newGroup.getDateOfFinish();
        if (!Objects.equals(dateOfFinish, newDateOfFinish)) {
            group.setDateOfFinish(newDateOfFinish);
            log.info("Group with id = {} changed name from {} to {}",
                    id, dateOfFinish, newDateOfFinish);
        }
        String message = String.format("Group with groupId = %s has successfully updated", id);
        return Response.builder()
                .httpStatus(RESET_CONTENT)
                .message(message)
                .build();
    }
}
