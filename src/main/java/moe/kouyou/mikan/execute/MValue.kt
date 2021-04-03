package moe.kouyou.mikan.execute

sealed class MValue

class MInteger: MValue()

class MFloat: MValue()

class MString: MValue()

class MBoolean: MValue()
