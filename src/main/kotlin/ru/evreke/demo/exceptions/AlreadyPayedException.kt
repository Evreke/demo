package ru.evreke.demo.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT)
class AlreadyPayedException(msg: String) : Exception(msg)