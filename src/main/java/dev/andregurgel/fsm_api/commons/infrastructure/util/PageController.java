package dev.andregurgel.fsm_api.commons.infrastructure.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PageController<T, F> {
    ResponseEntity<Page<T>> findAllPage(Pageable pageable);
    ResponseEntity<Page<T>> findAllPageFiltered(Pageable pageable, F filter);
}
