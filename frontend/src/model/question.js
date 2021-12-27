import { get, post, put, _delete } from '@/lin/plugin/axios'

class Question {
  static async createQuestion(data) {
    const res = await post('v1/question', data)
    return res
  }

  static async deleteQuestion(id) {
    const res = await _delete(`v1/question/${id}`)
    return res
  }

  static async getQuestion(id) {
    const res = await get(`v1/question/${id}`)
    return res
  }

  static async updateQuestion(id, data) {
    const res = await put(`v1/question/${id}`, data)
    return res
  }

  static async getAllQuestion() {
    const res = await get('v1/question/')
    return res
  }

  static async getOptionsByQuestion(optionIds) {
    const res = await get(`v1/question/options?optionIds=${optionIds}`)
    return res
  }

  static async getQuestionByPage(page = 0, count = 10) {
    const res = await get('v1/question/page', { page, count })
    return res
  }
}

export default Question
