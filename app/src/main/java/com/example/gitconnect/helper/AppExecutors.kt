package com.example.gitconnect.helper

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {
    val diskIO: Executor = Executors.newSingleThreadExecutor()
}