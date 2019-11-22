package com.annevonwolffen.androidschool.todolist.data.db;

public class TaskSchema {
    public static final class TaskTable {
        public static final String TABLE_NAME = "TASKS";
        public static final class Cols {
            //public static final String UUID = "uuid";
            public static final String NAME = "NAME";
            public static final String IS_DONE = "IS_DONE";
        }
    }
}
