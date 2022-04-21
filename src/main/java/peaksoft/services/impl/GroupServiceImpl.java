package peaksoft.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.dto.GroupDto;
import peaksoft.mapper.GroupMapper;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.Group;
import peaksoft.dto.response.Response;
import peaksoft.repositories.GroupRepository;
import peaksoft.services.CourseService;
import peaksoft.services.GroupService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final CourseService service;
    private final GroupMapper mapper;

    @Override
    public Response saveGroup(GroupDto group, Long id) {
        Group group1 = mapper.create(group);
        group1.setCourse(service.getById(id));
        Group saveGroup = groupRepository.save(group1);
        return Response.builder()
                .httpStatus(CREATED)
                .message(String.format("Group with groupName = %s successfully registered",
                        saveGroup.getGroupName()))
                .build();
    }

    @Override
    public Response deleteById(Long id) {
        groupRepository.deleteById(id);
        String message = String.format("Group with id = %s has successfully deleted", id);
        return Response.builder()
                .httpStatus(OK)
                .message(message)
                .build();
    }

    @Override
    public Group getById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(
                            String.format("group with id = %s does not exists", id)
                    );
                });
    }

    @Override
    public List<Group> findAllGroup() {
        return groupRepository.findAll();
    }

    @Override
    @Transactional
    public Response updateById(Long id, GroupDto group) {
        Group groups = groupRepository.getById(id);
        String currentGroupName = groups.getGroupName();
        String newGroupName = group.getGroupName();
        if (Objects.equals(currentGroupName,newGroupName)){
            groups.setGroupName(newGroupName);
        }

        String dateOfStart = String.valueOf(groups.getDateOfStart());
        String newDateOfStart = groups.getDateOfStart();
        if (!Objects.equals(dateOfStart, newDateOfStart)) {
            groups.setDateOfStart(String.valueOf(LocalDate.parse(newDateOfStart)));
        }
        String dateOfFinish = String.valueOf(groups.getDateOfFinish());
        String newDateOfFinish = groups.getDateOfFinish();
        if (!Objects.equals(dateOfFinish, newDateOfFinish)) {
            groups.setDateOfFinish(String.valueOf(LocalDate.parse(newDateOfFinish)));
        }
        String message = String.format("Group with groupId = %s has successfully updated", id);
        return Response.builder()
                .httpStatus(RESET_CONTENT)
                .message(message)
                .build();
    }
}
