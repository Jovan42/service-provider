package service.sharedlib.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
public class CustomPage<T> {

    public CustomPage(Page<T> page){
        this.content = page.getContent();
        this.pageSize = page.getSize();
        this.pageNumber = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.hasContent = page.hasContent();
        this.sort  = page.getSort();
    }
    private final List<T> content;
    private final Integer pageSize;
    private final Integer pageNumber;
    private final Integer totalPages;
    private final Long totalElements;
    private final boolean hasContent;
    private final Sort sort;

}
