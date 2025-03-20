package com.example.id_maker_teacher.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.id_maker_teacher.Model.ClassModel;
import com.example.id_maker_teacher.Model.StudentModel;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "IDMaker";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_CLASS = "ClassTable";
    private static final String TABLE_STUDENT = "StudentTable";

    // ClassTable Columns
    private static final String COLUMN_CLASS_ID = "ClassId"; // Changed from TeacherId
    private static final String COLUMN_TEACHER_NAME = "TeacherName";
    private static final String COLUMN_TEACHER_PHONE = "TeacherPhone";
    private static final String COLUMN_CLASS = "Class";
    private static final String COLUMN_DIV = "Div";
    private static final String COLUMN_ORG_ID = "OrganizationId";
    private static final String COLUMN_ORG_NAME = "OrganizationName";
    private static final String COLUMN_ORG_ADDRESS = "OrganizationAddress";
    private static final String COLUMN_ORG_WEBSITE = "OrganizationWebsite";
    private static final String COLUMN_ORG_PHONE = "OrganizationPhone";
    private static final String COLUMN_ORG_EMAIL = "OrganizationEmail";
    private static final String COLUMN_ORG_LOGO = "OrganizationLogoPath";
    private static final String COLUMN_ORG_INSTRUCTION_TITLE = "OrganizationInstructionTitle";
    private static final String COLUMN_ORG_INSTRUCTION_DESC = "OrganizationInstructionDescription";
    private static final String COLUMN_ORG_PRINCIPAL_SIGNATURE = "OrganizationPrincipalSignature";

    // StudentTable Columns
    private static final String COLUMN_STUDENT_ID = "StudentId";
    private static final String COLUMN_STUDENT_CLASS_ID = "StudentClassId";
    private static final String COLUMN_STUDENT_ROLL_NUMBER = "StudentRollNumber";
    private static final String COLUMN_STUDENT_NAME = "StudentFullName";
    private static final String COLUMN_STUDENT_CLASS = "Class";
    private static final String COLUMN_DOB = "DateOfBirth";
    private static final String COLUMN_STUDENT_DIV = "Div";
    private static final String COLUMN_BLOOD_GROUP = "BloodGroup";
    private static final String COLUMN_PROFILE_IMAGE = "ProfileImage";
    private static final String COLUMN_PARENT_ONE_TITLE = "ParentOneTitle";
    private static final String COLUMN_PARENT_ONE_NAME = "ParentOneName";
    private static final String COLUMN_PARENT_ONE_PHONE = "ParentOnePhone";
    private static final String COLUMN_PARENT_TWO_TITLE = "ParentTwoTitle";
    private static final String COLUMN_PARENT_TWO_NAME = "ParentTwoName";
    private static final String COLUMN_PARENT_TWO_PHONE = "ParentTwoPhone";
    private static final String COLUMN_HOME_ADDRESS = "HomeAddress";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CLASS_TABLE = "CREATE TABLE " + TABLE_CLASS + "("
                + COLUMN_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " // Auto Increment Added
                + COLUMN_TEACHER_NAME + " TEXT, "
                + COLUMN_TEACHER_PHONE + " TEXT, "
                + COLUMN_CLASS + " TEXT, "
                + COLUMN_DIV + " TEXT, "
                + COLUMN_ORG_ID + " INTEGER, "
                + COLUMN_ORG_NAME + " TEXT, "
                + COLUMN_ORG_ADDRESS + " TEXT, "
                + COLUMN_ORG_WEBSITE + " TEXT, "
                + COLUMN_ORG_PHONE + " TEXT, "
                + COLUMN_ORG_EMAIL + " TEXT, "
                + COLUMN_ORG_LOGO + " TEXT, "
                + COLUMN_ORG_INSTRUCTION_TITLE + " TEXT, "
                + COLUMN_ORG_INSTRUCTION_DESC + " TEXT, "
                + COLUMN_ORG_PRINCIPAL_SIGNATURE + " TEXT)";

        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + "("
                + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " // Auto Increment Added
                + COLUMN_STUDENT_CLASS_ID + " INTEGER , " // Auto Increment Added
                + COLUMN_STUDENT_ROLL_NUMBER + " INTEGER, "
                + COLUMN_STUDENT_NAME + " TEXT, "
                + COLUMN_STUDENT_CLASS + " TEXT, "
                + COLUMN_DOB + " TEXT, "
                + COLUMN_STUDENT_DIV + " TEXT, "
                + COLUMN_BLOOD_GROUP + " TEXT, "
                + COLUMN_PROFILE_IMAGE + " TEXT, "
                + COLUMN_PARENT_ONE_TITLE + " TEXT, "
                + COLUMN_PARENT_ONE_NAME + " TEXT, "
                + COLUMN_PARENT_ONE_PHONE + " TEXT, "
                + COLUMN_PARENT_TWO_TITLE + " TEXT, "
                + COLUMN_PARENT_TWO_NAME + " TEXT, "
                + COLUMN_PARENT_TWO_PHONE + " TEXT, "
                + COLUMN_HOME_ADDRESS + " TEXT)";

        db.execSQL(CREATE_CLASS_TABLE);
        db.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }

    // Function to add a class to the database
    public long addClass(ClassModel classModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TEACHER_NAME, classModel.getTeacherName());
        values.put(COLUMN_TEACHER_PHONE, classModel.getTeacherPhone());
        values.put(COLUMN_CLASS, classModel.getClassName());
        values.put(COLUMN_DIV, classModel.getDiv());
        values.put(COLUMN_ORG_ID, classModel.getOrganizationId());
        values.put(COLUMN_ORG_NAME, classModel.getOrganizationName());
        values.put(COLUMN_ORG_ADDRESS, classModel.getOrganizationAddress());
        values.put(COLUMN_ORG_WEBSITE, classModel.getOrganizationWebsite());
        values.put(COLUMN_ORG_PHONE, classModel.getOrganizationPhone());
        values.put(COLUMN_ORG_EMAIL, classModel.getOrganizationEmail());
        values.put(COLUMN_ORG_LOGO, classModel.getOrganizationLogoPath());
        values.put(COLUMN_ORG_INSTRUCTION_TITLE, classModel.getOrganizationInstructionTitle());
        values.put(COLUMN_ORG_INSTRUCTION_DESC, classModel.getOrganizationInstructionDescription());
        values.put(COLUMN_ORG_PRINCIPAL_SIGNATURE, classModel.getOrganizationPrincipalSignature());

        long result = db.insert(TABLE_CLASS, null, values);
        db.close();
        return result; // Returns the row ID if successful, -1 if failed
    }

    public int updateClass(ClassModel classModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TEACHER_NAME, classModel.getTeacherName());
        values.put(COLUMN_TEACHER_PHONE, classModel.getTeacherPhone());
        values.put(COLUMN_CLASS, classModel.getClassName());
        values.put(COLUMN_DIV, classModel.getDiv());
     /*   values.put(COLUMN_ORG_ID, classModel.getOrganizationId());
        values.put(COLUMN_ORG_NAME, classModel.getOrganizationName());
        values.put(COLUMN_ORG_ADDRESS, classModel.getOrganizationAddress());
        values.put(COLUMN_ORG_WEBSITE, classModel.getOrganizationWebsite());
        values.put(COLUMN_ORG_PHONE, classModel.getOrganizationPhone());
        values.put(COLUMN_ORG_EMAIL, classModel.getOrganizationEmail());
        values.put(COLUMN_ORG_LOGO, classModel.getOrganizationLogoPath());
        values.put(COLUMN_PARENT_TWO_TITLE, classModel.getOrganizationInstructionTitle());
        values.put(COLUMN_ORG_INSTRUCTION_DESC, classModel.getOrganizationInstructionDescription());
        values.put(COLUMN_ORG_PRINCIPAL_SIGNATURE, classModel.getOrganizationPrincipalSignature());*/

        // Updating row where class ID matches
        int rowsAffected = db.update(TABLE_CLASS, values, COLUMN_CLASS_ID + " = ?",
                new String[]{String.valueOf(classModel.getClassId())});

        db.close();
        return rowsAffected; // Returns the number of rows updated (should be 1 if successful)
    }

    // Function to delete a class from the database using Class ID
    public boolean deleteClass(int classId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_CLASS, COLUMN_CLASS_ID + " = ?", new String[]{String.valueOf(classId)});
        db.close();
        return deletedRows > 0; // Returns true if deletion was successful
    }



    // Function to get class details by Class ID
    public ClassModel getClassDetailsById(int classId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ClassModel classModel = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CLASS + " WHERE " + COLUMN_CLASS_ID + " = ?",
                new String[]{String.valueOf(classId)});

        if (cursor.moveToFirst()) {
            classModel = new ClassModel();
            classModel.setClassId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLASS_ID)));
            classModel.setTeacherName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEACHER_NAME)));
            classModel.setTeacherPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEACHER_PHONE)));
            classModel.setClassName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASS)));
            classModel.setDiv(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIV)));
            classModel.setOrganizationId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORG_ID)));
            classModel.setOrganizationName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORG_NAME)));
            classModel.setOrganizationAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORG_ADDRESS)));
            classModel.setOrganizationWebsite(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORG_WEBSITE)));
            classModel.setOrganizationPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORG_PHONE)));
            classModel.setOrganizationEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORG_EMAIL)));
            classModel.setOrganizationLogoPath(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORG_LOGO)));
            classModel.setOrganizationInstructionTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORG_INSTRUCTION_TITLE)));
            classModel.setOrganizationInstructionDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORG_INSTRUCTION_DESC)));
            classModel.setOrganizationPrincipalSignature(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORG_PRINCIPAL_SIGNATURE)));
        }

        cursor.close();
        db.close();
        return classModel; // Returns class details for the given Class ID
    }

    // Function to get all classes from the database
    public ArrayList<ClassModel> getAllClasses() {
        ArrayList<ClassModel> classList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CLASS, null);

        if (cursor.moveToFirst()) {
            do {
                ClassModel classModel = new ClassModel();
                classModel.setClassId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLASS_ID)));
                classModel.setTeacherName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEACHER_NAME)));
                classModel.setTeacherPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEACHER_PHONE)));
                classModel.setClassName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASS)));
                classModel.setDiv(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIV)));
                classModel.setOrganizationId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORG_ID)));
                classModel.setOrganizationName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORG_NAME)));

                classList.add(classModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return classList; // Returns a list of all classes
    }


    public long addStudent(StudentModel student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_STUDENT_CLASS_ID, student.getClass_Id());
        values.put(COLUMN_STUDENT_ROLL_NUMBER, student.getStudentRollNumber());
        values.put(COLUMN_STUDENT_NAME, student.getStudentFullName());
        values.put(COLUMN_STUDENT_CLASS, student.getStudentClass());
        values.put(COLUMN_DOB, student.getDateOfBirth());
        values.put(COLUMN_DIV, student.getDiv());
        values.put(COLUMN_BLOOD_GROUP, student.getBloodGroup());
        values.put(COLUMN_PROFILE_IMAGE, student.getProfileImage());
        values.put(COLUMN_PARENT_ONE_TITLE, student.getParentOneTitle());
        values.put(COLUMN_PARENT_ONE_NAME, student.getParentOneName());
        values.put(COLUMN_PARENT_ONE_PHONE, student.getParentOnePhone());
        values.put(COLUMN_PARENT_TWO_TITLE, student.getParentTwoTitle());
        values.put(COLUMN_PARENT_TWO_NAME, student.getParentTwoName());
        values.put(COLUMN_PARENT_TWO_PHONE, student.getParentTwoPhone());
        values.put(COLUMN_HOME_ADDRESS, student.getHomeAddress());

        long id = db.insert(TABLE_STUDENT, null, values);
        db.close();
        return id; // Returns the ID of the newly inserted student
    }

    public boolean deleteStudent(int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_STUDENT, COLUMN_STUDENT_ID + " = ?",
                new String[]{String.valueOf(studentId)});
        db.close();
        return rowsDeleted > 0; // Returns true if deletion was successful
    }


    public boolean updateStudent(StudentModel student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_STUDENT_CLASS_ID, student.getClass_Id());
        values.put(COLUMN_STUDENT_ROLL_NUMBER, student.getStudentRollNumber());
        values.put(COLUMN_STUDENT_NAME, student.getStudentFullName());
        values.put(COLUMN_STUDENT_CLASS, student.getStudentClass());
        values.put(COLUMN_DOB, student.getDateOfBirth());
        values.put(COLUMN_DIV, student.getDiv());
        values.put(COLUMN_BLOOD_GROUP, student.getBloodGroup());
        values.put(COLUMN_PROFILE_IMAGE, student.getProfileImage());
        values.put(COLUMN_PARENT_ONE_TITLE, student.getParentOneTitle());
        values.put(COLUMN_PARENT_ONE_NAME, student.getParentOneName());
        values.put(COLUMN_PARENT_ONE_PHONE, student.getParentOnePhone());
        values.put(COLUMN_PARENT_TWO_TITLE, student.getParentTwoTitle());
        values.put(COLUMN_PARENT_TWO_NAME, student.getParentTwoName());
        values.put(COLUMN_PARENT_TWO_PHONE, student.getParentTwoPhone());
        values.put(COLUMN_HOME_ADDRESS, student.getHomeAddress());

        int rowsUpdated = db.update(TABLE_STUDENT, values, COLUMN_STUDENT_ID + " = ?",
                new String[]{String.valueOf(student.getStudentId())});
        db.close();
        return rowsUpdated > 0; // Returns true if update was successful
    }

    public boolean updateStudentImage(String path,String studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROFILE_IMAGE, path);
        int rowsUpdated = db.update(TABLE_STUDENT, values, COLUMN_STUDENT_ID + " = ?",
                new String[]{String.valueOf(studentId)});
        db.close();
        return rowsUpdated > 0; // Returns true if update was successful
    }

    public StudentModel getStudentById(int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        StudentModel student = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_STUDENT_ID + " = ?",
                new String[]{String.valueOf(studentId)});

        if (cursor.moveToFirst()) {
            student = new StudentModel();
            student.setStudentId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ID)));
            student.setClass_Id(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_CLASS_ID)));
            student.setStudentRollNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ROLL_NUMBER)));
            student.setStudentFullName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_NAME)));
            student.setStudentClass(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_CLASS)));
            student.setDateOfBirth(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB)));
            student.setDiv(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIV)));
            student.setBloodGroup(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BLOOD_GROUP)));
            student.setProfileImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_IMAGE)));
            student.setParentOneTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_ONE_TITLE)));
            student.setParentOneName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_ONE_NAME)));
            student.setParentOnePhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_ONE_PHONE)));
            student.setParentTwoTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_TWO_TITLE)));
            student.setParentTwoName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_TWO_NAME)));
            student.setParentTwoPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_TWO_PHONE)));
            student.setHomeAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOME_ADDRESS)));
        }

        cursor.close();
        db.close();
        return student; // Returns student details
    }

    public ArrayList<StudentModel> getAllStudents() {
        ArrayList<StudentModel> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENT, null);

        if (cursor.moveToFirst()) {
            do {
                StudentModel student = new StudentModel();
                student.setStudentId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ID)));
                student.setClass_Id(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_CLASS_ID)));
                student.setStudentRollNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ROLL_NUMBER)));
                student.setStudentFullName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_NAME)));
                student.setStudentClass(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_CLASS)));
                student.setDateOfBirth(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB)));
                student.setDiv(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIV)));
                student.setBloodGroup(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BLOOD_GROUP)));
                student.setProfileImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_IMAGE)));
                student.setParentOneTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_ONE_TITLE)));
                student.setParentOneName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_ONE_NAME)));
                student.setParentOnePhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_ONE_PHONE)));
                student.setParentTwoTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_TWO_TITLE)));
                student.setParentTwoName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_TWO_NAME)));
                student.setParentTwoPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_TWO_PHONE)));
                student.setHomeAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOME_ADDRESS)));

                studentList.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList; // Returns a list of all students
    }
    public ArrayList<StudentModel> getAllStudentsUsingClassId(String classId) {
        ArrayList<StudentModel> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_STUDENT_CLASS_ID + " = ?", new String[]{classId});

        if (cursor.moveToFirst()) {
            do {
                StudentModel student = new StudentModel();
                student.setStudentId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ID)));
                student.setClass_Id(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_CLASS_ID)));
                student.setStudentRollNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ROLL_NUMBER)));
                student.setStudentFullName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_NAME)));
                student.setStudentClass(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_CLASS)));
                student.setDateOfBirth(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB)));
                student.setDiv(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIV)));
                student.setBloodGroup(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BLOOD_GROUP)));
                student.setProfileImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_IMAGE)));
                student.setParentOneTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_ONE_TITLE)));
                student.setParentOneName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_ONE_NAME)));
                student.setParentOnePhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_ONE_PHONE)));
                student.setParentTwoTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_TWO_TITLE)));
                student.setParentTwoName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_TWO_NAME)));
                student.setParentTwoPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_TWO_PHONE)));
                student.setHomeAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOME_ADDRESS)));

                studentList.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList; // Returns a list of all students
    }


    // Function to add a list of students in a single transaction
    public void addStudentList(ArrayList<StudentModel> studentList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction(); // Start a transaction for efficiency

        try {
            for (StudentModel student : studentList) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_STUDENT_CLASS_ID, student.getClass_Id());
                values.put(COLUMN_STUDENT_ROLL_NUMBER, student.getStudentRollNumber());
                values.put(COLUMN_STUDENT_NAME, student.getStudentFullName());
                values.put(COLUMN_STUDENT_CLASS, student.getStudentClass());
                values.put(COLUMN_DOB, student.getDateOfBirth());
                values.put(COLUMN_DIV, student.getDiv());
                values.put(COLUMN_BLOOD_GROUP, student.getBloodGroup());
                values.put(COLUMN_PROFILE_IMAGE, student.getProfileImage());
                values.put(COLUMN_PARENT_ONE_TITLE, student.getParentOneTitle());
                values.put(COLUMN_PARENT_ONE_NAME, student.getParentOneName());
                values.put(COLUMN_PARENT_ONE_PHONE, student.getParentOnePhone());
                values.put(COLUMN_PARENT_TWO_TITLE, student.getParentTwoTitle());
                values.put(COLUMN_PARENT_TWO_NAME, student.getParentTwoName());
                values.put(COLUMN_PARENT_TWO_PHONE, student.getParentTwoPhone());
                values.put(COLUMN_HOME_ADDRESS, student.getHomeAddress());

                db.insert(TABLE_STUDENT, null, values);
            }
            db.setTransactionSuccessful(); // Mark transaction as successful
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
        } finally {
            db.endTransaction(); // End transaction
            db.close();
        }
    }








}

