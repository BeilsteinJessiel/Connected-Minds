//Imports
import java.util.ArrayList
import android.widget.Button
import android.widget.TextView

//Main Class
class OnlineEducationPlatform {
    private var courseList = ArrayList<Course>()  // list to store course objects
    private var studentList = ArrayList<Student>()  // list to store student objects
  
    // Constructor
    init {
        // Create some courses to begin with
        this.courseList.add(Course("Computer Science", "Learn the basics of programming"))
        this.courseList.add(Course("Mathematics", "Learn the basics of mathematics"))
        this.courseList.add(Course("English Literature", "Learn the classic works of literature"))
    }

    // Add a student to the platform
    fun addStudent(student: Student) {
        this.studentList.add(student)
    }

    // Get a student from the platform
    fun getStudent(name: String) : Student? {
        for (student in studentList) {
            if (student.name == name) {
                return student
            }
        }
        return null
    }

    // Enroll student into a course
    fun enrollStudentInCourse(student: Student, course: Course) {
        student.enrollInCourse(course)
    }

    // Unenroll student from a course
    fun unenrollStudentFromCourse(student: Student, course: Course) {
        student.unenrollFromCourse(course)
    }

    // Add a new course to the platform
    fun addCourse(course: Course) {
        this.courseList.add(course)
    }

    // Get a list of all courses
    fun getCourseList() : ArrayList<Course> {
        return this.courseList
    }

    // Class to represent a course
    class Course (val name : String, val description : String) {
        private var studentList : ArrayList<Student> = ArrayList()  // list to store students enrolled in this course

        // Add a student to this course
        fun addStudent(student: Student) {
            this.studentList.add(student)
        }

        // Remove a student from this course
        fun removeStudent(student: Student) {
            this.studentList.remove(student)
        }

        // Get a list of all students enrolled in this course
        fun getStudentList() : ArrayList<Student> {
            return this.studentList
        }
    }

    // Class to represent a student
    class Student(val name : String, val email : String) {
        private var courseList = ArrayList<Course>()  // list to store courses enrolled in

        // Enroll a student in a course
        fun enrollInCourse(course: Course) {
            this.courseList.add(course)
            course.addStudent(this)
        }

        // Unenroll a student from a course
        fun unenrollFromCourse(course: Course) {
            this.courseList.remove(course)
            course.removeStudent(this)
        }

        // Get a list of courses this student is enrolled in
        fun getCourseList() : ArrayList<Course> {
            return this.courseList
        }
    }
    
    // View for the course list
    class CourseListView(val context: Context) {
        private var coursesList = ArrayList<Course>()  // list of courses to display
        private var textView: TextView? = null  // textview to display courses list
        private var enrollButton: Button? = null  // button to enroll in course

        // Constructor
        init {
            this.textView = TextView(context)
            this.enrollButton = Button(context)
            this.enrollButton?.text = "Enroll"
        }

        // Set the list of courses to display
        fun setCoursesList(coursesList: ArrayList<Course>) {
            this.coursesList = coursesList
        }

        // Render the view
        fun render() {
            // Clear the view
            this.textView?.text = ""

            // Append each course description to the view
            for (course in coursesList) {
                textView?.append("${course.name}: ${course.description}\n")
            }

            // Set onclick listener for the enroll button
            enrollButton?.setOnClickListener {
                // Show dialog for selecting course to enroll in
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Select Course")
                    .setItems(coursesList.map { it.name }.toTypedArray()) { _, which ->
                        // Get the course selected
                        val selectedCourse = coursesList[which]

                        // Show dialog for entering student name
                        val studentNameView = EditText(context)
                        val studentEmailView = EditText(context)
                        val studentDialogBuilder = AlertDialog.Builder(context)
                        studentDialogBuilder.setTitle("Enter Student Name")
                            .setView(studentNameView)
                            .setView(studentEmailView)
                            .setPositiveButton("Submit") { _, _ ->
                                // Get the student name entered
                                val studentName = studentNameView.text.toString()
                                val studentEmail = studentEmailView.text.toString()

                                // Create the student
                                val student = Student(studentName, studentEmail)

                                // Enroll the student in the course
                                OnlineEducationPlatform.enrollStudentInCourse(student, selectedCourse)

                                // Notify user of success
                                Toast.makeText(context, "Student enrolled in course", Toast.LENGTH_SHORT).show()
                            }
                            .create()
                            .show()
                    }
                    .create()
                    .show()
            }
        }
    }
}