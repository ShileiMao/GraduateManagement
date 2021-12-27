import { get, post, put, _delete } from '@/lin/plugin/axios'

class Topic {
  static async createTopic(data) {
    const res = await post('v1/topic', data)
    return res
  }

  static async deleteTopic(id) {
    const res = await _delete(`v1/topic/${id}`)
    return res
  }

  static async getTopic(id) {
    const res = await get(`v1/topic/${id}`)
    return res
  }

  static async getTopics() {
    const res = await get('v1/topic/by/year')
    return res
  }

  static async getSupTopics() {
    const res = await get('v1/topic/sup/by/year')
    return res
  }

  static async updateTopic(id, data) {
    const res = await put(`v1/topic/${id}`, data)
    return res
  }

  static async getTopicByPage(keyword, page = 0, count = 10) {
    let res = null
    if (keyword !== '') {
      res = await get('v1/topic/page', { page, count, keyword })
    } else {
      res = await get('v1/topic/page', { page, count })
    }
    return res
  }

  static async updateStatus(data) {
    const res = await put('v1/topic/status', data)
    return res
  }

  static async getTopicByStudentSid(sid) {
    const res = await get(`v1/topic/getTopicByStudentSid?sid=${sid}`)
    return res
  }

  static async getTopicsByTeacherId(teacherId) {
    const res = await get(`v1/topic/getTopicsByTeacherId?teacherId=${teacherId}`)
    return res
  }

  static async getSupTopicsByTeacherId(teacherId) {
    const res = await get(`v1/topic/getSupTopicsByTeacherId?teacherId=${teacherId}`)
    return res
  }
}

export default Topic
