import { get, post, put, _delete } from '@/lin/plugin/axios'

class Teacher {
  static async createTeacher(data) {
    const res = await post('v1/teacher', data)
    return res
  }

  static async deleteTeacher(id) {
    const res = await _delete(`v1/teacher/${id}`)
    return res
  }

  static async getTeacher(id) {
    const res = await get(`v1/teacher/${id}`)
    return res
  }

  static async getAllTeacher() {
    const res = await get('v1/teacher/all')
    return res
  }

  static async editTeacher(id, data) {
    const res = await put(`v1/teacher/${id}`, data)
    return res
  }

  static async getTeacherByPage(keyword, page = 0, count = 10) {
    let res = null
    if (keyword !== '') {
      res = await get('v1/teacher/page', { page, count, keyword })
    } else {
      res = await get('v1/teacher/page', { page, count })
    }
    return res
  }
}

export default Teacher
