package com.annevonwolffen.androidschool.todolist.data.db;

public class TaskSchema {
    public static final class TaskTable {
        public static final String TABLE_NAME = "tasks";
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
        }
    }
}
