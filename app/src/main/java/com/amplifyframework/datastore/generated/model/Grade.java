package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.ModelIdentifier;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Grade type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Grades", type = Model.Type.USER, version = 1)
public final class Grade implements Model {
  public static final QueryField ID = field("Grade", "id");
  public static final QueryField EXAMS = field("Grade", "examsGradeId");
  public static final QueryField EXAM_GRADE = field("Grade", "examGrade");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Exams") @BelongsTo(targetName = "examsGradeId", targetNames = {"examsGradeId"}, type = Exams.class) Exams exams;
  private final @ModelField(targetType="Int", isRequired = true) Integer examGrade;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public Exams getExams() {
      return exams;
  }
  
  public Integer getExamGrade() {
      return examGrade;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Grade(String id, Exams exams, Integer examGrade) {
    this.id = id;
    this.exams = exams;
    this.examGrade = examGrade;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Grade grade = (Grade) obj;
      return ObjectsCompat.equals(getId(), grade.getId()) &&
              ObjectsCompat.equals(getExams(), grade.getExams()) &&
              ObjectsCompat.equals(getExamGrade(), grade.getExamGrade()) &&
              ObjectsCompat.equals(getCreatedAt(), grade.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), grade.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getExams())
      .append(getExamGrade())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Grade {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("exams=" + String.valueOf(getExams()) + ", ")
      .append("examGrade=" + String.valueOf(getExamGrade()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static ExamGradeStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Grade justId(String id) {
    return new Grade(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      exams,
      examGrade);
  }
  public interface ExamGradeStep {
    BuildStep examGrade(Integer examGrade);
  }
  

  public interface BuildStep {
    Grade build();
    BuildStep id(String id);
    BuildStep exams(Exams exams);
  }
  

  public static class Builder implements ExamGradeStep, BuildStep {
    private String id;
    private Integer examGrade;
    private Exams exams;
    public Builder() {
      
    }
    
    private Builder(String id, Exams exams, Integer examGrade) {
      this.id = id;
      this.exams = exams;
      this.examGrade = examGrade;
    }
    
    @Override
     public Grade build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Grade(
          id,
          exams,
          examGrade);
    }
    
    @Override
     public BuildStep examGrade(Integer examGrade) {
        Objects.requireNonNull(examGrade);
        this.examGrade = examGrade;
        return this;
    }
    
    @Override
     public BuildStep exams(Exams exams) {
        this.exams = exams;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, Exams exams, Integer examGrade) {
      super(id, exams, examGrade);
      Objects.requireNonNull(examGrade);
    }
    
    @Override
     public CopyOfBuilder examGrade(Integer examGrade) {
      return (CopyOfBuilder) super.examGrade(examGrade);
    }
    
    @Override
     public CopyOfBuilder exams(Exams exams) {
      return (CopyOfBuilder) super.exams(exams);
    }
  }
  

  public static class GradeIdentifier extends ModelIdentifier<Grade> {
    private static final long serialVersionUID = 1L;
    public GradeIdentifier(String id) {
      super(id);
    }
  }
  
}
