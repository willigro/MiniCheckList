package com.rittamann.minichecklist.exceptions

class ItemNameEmptyException : Exception() {
    override fun toString(): String {
        return "Nome do item não pode estar em branco"
    }
}