package peaksoft.mapper;

import lombok.Getter;
import org.springframework.stereotype.Component;
import peaksoft.dto.GroupDto;
import peaksoft.models.Group;

@Component
public class GroupMapper {
    public Group create(GroupDto dto) {
        if (dto == null) {
            return null;
        }
        Group group = new Group();
        group.setGroupName(dto.getGroupName());
        group.setDateOfStart(dto.getDateOfStart());
        group.setDateOfFinish(dto.getDateOfFinish());

        return group;
    }
}
