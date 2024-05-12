package org.fullstack4.springboot2.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value={AuditingEntityListener.class})
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "reg_date", updatable = false) // 아래 필드명이랑 동일하면 name은 안 써도 무관. 생성 시점에 값이 들어가고 그 이후로 업데이트 일어나면 안되니까.
    private LocalDateTime reg_date;

    @LastModifiedDate
    @Column(name = "modify_date", nullable = true, insertable = false, updatable = true)
    private LocalDateTime modify_date;

    public void setModify_date(LocalDateTime modify_date) {
        this.modify_date = LocalDateTime.now();
    }
}
