import { get, post, put, _delete } from '@/lin/plugin/axios'
import College from './college'

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


  static async getTeacherByLognName(loginName) {
    const url = `v1/teacher/tid/${loginName}`
    const res = await get(url)
    console.log("teacher info: " + JSON.stringify(res))
    return res
  }

  static async getCollegeByTeacherId(teacherId) {
    const res = await get(`v1/teacher/${teacherId}`)
    if(res != null) {
      const collegeInfo = await College.getCollege(res.college_id)
      return collegeInfo
    }
    return null
  }
}

export default Teacher
