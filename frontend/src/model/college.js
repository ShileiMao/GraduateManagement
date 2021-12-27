import { get, post, put, _delete } from '@/lin/plugin/axios'

class College {
  static async createCollege(data) {
    const res = await post('v1/college', data)
    return res
  }

  static async deleteCollege(id) {
    const res = await _delete(`v1/college/${id}`)
    return res
  }

  static async getCollege(id) {
    const res = await get(`v1/college/${id}`)
    return res
  }

  static async updateCollege(data) {
    const res = await put(`v1/college/${data.id}`, data)
    return res
  }

  static async getAllCollege() {
    const res = await get('v1/college/all')
    return res
  }

  static async getCollegeByPage(page = 0, count = 2) {
    const res = await get('v1/college/page', { page, count })
    return res
  }
}

export default College
