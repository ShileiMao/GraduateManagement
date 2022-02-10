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
    console.log("updating assign: " + JSON.stringify(data))
    const res = await post('v1/topicSelect/updateAssign', data)
    return res
  }

  static async getTopicByStudentSid(sid) {
    const res = await get(`v1/topic/getTopicByStudentSid?sid=${sid}`)
    return res
  }

  // static async getTopicsByTeacherId(teacherId) {
  //   const res = await get(`v1/topic/getTopicsByTeacherId?teacherId=${teacherId}`)
  //   return res
  // }

  static async getAssignByTeacherId(graduateYearId, teacherId) {
    const url = `v1/topicSelect/getAllSelection?graduateInfoId=${graduateYearId}&teacherId=${teacherId}`    
    
    const res = await get(url)
    console.log("posting to url: " + url)
    return res
  }

  static async getSupTopicsByTeacherId(teacherId) {
    const res = await get(`v1/topic/getSupTopicsByTeacherId?teacherId=${teacherId}`)
    return res
  }

  static async getTasksByTeacherId(graduateYearId, teacherId) {
    let url = `v1/topicSelect/loadAll?graduateInfoId=${graduateYearId}`
    if(teacherId != null) {
      url = `v1/topicSelect/loadAll?graduateInfoId=${graduateYearId}&teacherId=${teacherId}`
    }
    
    const res = await post(url)
    console.log("posting to url: " + url)
    return res
  }

  static async getAssignByTaskId(graduateYearId, topicId) {
    let url = `v1/topicSelect/getAllSelection?graduateInfoId=${graduateYearId}&topicId=${topicId}`
    
    const res = await get(url)
    console.log("posting to url: " + url)
    return res
  }


  static async deleteTopicById(topicId) {
    const url = `v1/topicSelect/delete?topicId=${topicId}`
    const res = await post(url)
    console.log("delete assign response: " + res)
    return res
  }

  static async allTopicTypes() {
    const url = `v1/topicSelect/allTopicType`
    const res = await get(url)
    return res
  }
  static async createNewTopic(data) {
    const url = `v1/topicSelect/createNewTask`
    const res = await post(url, data)

    console.log("create topic response: " + JSON.stringify(res))
    return res
  }

  static async updateExistingTopic(data) {
    const url = `v1/topicSelect/updateTask`
    const res = await post(url, data)

    console.log("create topic response: " + JSON.stringify(res))
    return res
  }

  static async selectTopic(topicId, studentId) {
    const url = `v1/topicSelect/assignTopic?topicId=${topicId}&studentId=${studentId}`
    const res = await post(url)
    console.log("create new assign: " + JSON.stringify(res))
    return res
  }
  
  static async getAssignByStudent(studentId) {
    const url = `v1/topicSelect/getAssign?studentId=${studentId}`
    const res = await get(url)
    return res
  }

  static async deleteAssignById(assignId) {
    const url = `v1/topicSelect/deleteAssign?assignId=${assignId}`
    await post(url)
    console.log("posting to : " + url)
  }

  
}

export default Topic
