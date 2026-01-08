package dev.andregurgel.fsm_api.controller.filter;

import dev.andregurgel.fsm_api.model.GroupInvite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupInviteFilter extends GroupInvite {
    private Long groupId;
}
