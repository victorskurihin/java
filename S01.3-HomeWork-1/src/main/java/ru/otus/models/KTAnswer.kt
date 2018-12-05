package ru.otus.models

class KTAnswer : IAnswer
{
    private var answer: String? = null

    private var score: Int = 0

    override fun getAnswer(): String?
    {
        return answer
    }

    override fun setAnswer(answer: String)
    {
        this.answer = answer
    }

    override fun getScore(): Int
    {
        return score
    }

    override fun setScore(score: Int)
    {
        this.score = score
    }

    override fun hashCode(): Int
    {
        return score + if (null != answer) 13 * answer!!.hashCode() else 0
    }

    override fun equals(obj: Any?): Boolean
    {
        if (obj == null) {
            return false
        }
        if (obj !is KTAnswer) {
            return false
        }
        val other = obj as KTAnswer?

        return (if (null != answer) answer == other!!.answer else null == other!!.answer) && score == other.score
    }

    override fun toString(): String
    {
        return this.javaClass.simpleName + "{ answer=\"" + answer + "\", score=" + score + " }"
    }
}
