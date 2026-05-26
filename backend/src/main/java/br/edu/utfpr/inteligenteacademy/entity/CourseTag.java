package br.edu.utfpr.inteligenteacademy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CourseTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public CourseTag() {}
    
	public CourseTag(Course course, Tag tag) {
		this.course = course;
		this.tag = tag;
	}

	public Course getCurso() {
		return course;
	}

	public void setCurso(Course course) {
		this.course = course;
	}

	public Tag getEtiqueta() {
		return tag;
	}

	public void setEtiqueta(Tag tag) {
		this.tag = tag;
	}

	public Long getId() {
		return id;
	}
    
    
}
