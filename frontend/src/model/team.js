import { get, post, put, _delete } from '@/lin/plugin/axios'

class Team {
  static async createTeam(data) {
    const res = await post('v1/team', data)
    return res
  }

  static async deleteTeam(id) {
    const res = await _delete(`v1/team/${id}`)
    return res
  }

  static async getTeam(id) {
    const res = await get(`v1/team/${id}`)
    return res
  }

  static async updateTeam(id, data) {
    const res = await put(`v1/team/${id}`, data)
    return res
  }

  static async getAllTeam() {
    const res = await get('v1/team/all')
    return res
  }

  static async getTeamByPage(keyword, page = 0, count = 10) {
    let res = null
    if (keyword !== '') {
      res = await get('v1/team/page', { page, count, keyword })
    } else {
      res = await get('v1/team/page', { page, count })
    }
    return res
  }

  static async getTeamStudents(data) {
    const res = await get(`v1/team/students?studentSids=${data}`)
    return res
  }

  static async deleteTeamStudents(sid, teamId) {
    const res = await _delete(`v1/team/students?studentSids=${sid}&teamId=${teamId}`)
    return res
  }

  static async getAllStudents(collegeId) {
    const res = await get(`v1/team/studentsInCollege?collegeId=${collegeId}`)
    return res
  }
}

export default Team
