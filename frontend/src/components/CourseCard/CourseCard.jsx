export default function CourseCard({ course }) {
    return (
        <div className="course-card">
            <div className="course-image">
                <div className="course-tags">
                    <span className="course-level">{course.level}</span>
                    <span className="course-category">{course.category}</span>
                    <span className="course-duration">{course.duration}</span>
                </div>
                <img src={course.image} alt={course.title} className="course-image" />
            </div>
            <div>
                <h3 className="course-title">{course.title}</h3>
                <p className="course-description">{course.description}</p>
                <div>
                    <span className="course-grade">{course.grade}</span>
                    <span className="course-requirement">{course.requirement}</span>
                    <button className="enroll-button">Inscrever-se</button>
                </div>
            </div>
        </div>
    );
}