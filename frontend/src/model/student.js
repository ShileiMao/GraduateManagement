import { get, post, put, _delete } from '@/lin/plugin/axios'

class Student {
  static async createStudent(data) {
    const res = await post('v1/student', data)
    return res
  }

  static async deleteStudent(id) {
    const res = await _delete(`v1/student/${id}`)
    return res
  }

  static async getStudent(id) {
    const res = await get(`v1/student/${id}`)
    return res
  }

  static async editStudent(id, data) {
    const res = await put(`v1/student/${id}`, data)
    return res
  }

  static async getStudentByPage(keyword, page = 0, count = 10) {
    let res = null
    if (keyword !== '') {
      res = await get('v1/student/page', { page, count, keyword })
    } else {
      res = await get('v1/student/page', { page, count })
    }
    return res
  }
}

export default Student
