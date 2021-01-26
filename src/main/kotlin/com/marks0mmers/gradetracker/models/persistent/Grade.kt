package com.marks0mmers.gradetracker.models.persistent

import com.marks0mmers.gradetracker.models.dto.GradeDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.Max
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

@Document
data class Grade(
    @Id
    val id: String?,

    @NotNull
    var name: String,

    @PositiveOrZero @Max(100)
    var grade: Double,

    @NotNull
    val gradeCategoryId: String
) {
    constructor(g: GradeDto) : this(
        g.id,
        g.name,
        g.grade,
        g.gradeCategoryId
    )
}