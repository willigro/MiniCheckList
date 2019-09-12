package com.rittamann.minichecklist.exceptions

class ItemPositionLessZeroException : Exception(){
    override fun toString(): String {
        return "Posição do item deve ser maior ou igual a 0"
    }
}