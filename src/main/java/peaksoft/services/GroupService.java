package peaksoft.services;

import peaksoft.dto.GroupDto;
import peaksoft.models.Group;
import peaksoft.dto.response.Response;

import java.util.List;

public interface GroupService {

    Response saveGroup(GroupDto group,Long id);

    Response deleteById(Long id);

    Group getById(Long id);

    List<Group> findAllGroup();

    Response updateById(Long id, GroupDto group);
}
