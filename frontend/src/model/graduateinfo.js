import { get, post, put, _delete } from '@/lin/plugin/axios'

class GraduateInfo {
  static async createGraduateInfo(data) {
    const res = await post('v1/graduate-info', data)
    return res
  }

  static async deleteGraduateInfo(id) {
    const res = await _delete(`v1/graduate-info/${id}`)
    return res
  }

  static async getGraduateInfo(id) {
    const res = await get(`v1/graduate-info/${id}`)
    return res
  }

  static async getGraduateInfoAll() {
    const res = await get('v1/graduate-info/all')
    return res
  }

  static async getGuideTeachersByGraduateId(id) {
    const res = await get(`v1/graduate-info/${id}/guide/teachers`)
    return res
  }

  static async updateGraduateInfo(data) {
    const res = await put(`v1/graduate-info/${data.id}`, data)
    return res
  }

  static async getGraduateInfoByPage(keyword, page = 0, count = 10) {
    let res = null
    if (keyword !== '') {
      res = await get('v1/graduate-info/page', { page, count, keyword })
    } else {
      res = await get('v1/graduate-info/page', { page, count })
    }
    return res
  }
}

export default GraduateInfo
