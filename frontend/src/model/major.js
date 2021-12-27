import { get, post, put, _delete } from '@/lin/plugin/axios'

class Major {
  static async createMajor(data) {
    const res = await post('v1/major', data)
    return res
  }

  static async deleteMajor(id) {
    const res = await _delete(`v1/major/${id}`)
    return res
  }

  static async getMajor(id) {
    const res = await get(`v1/major/${id}`)
    return res
  }

  static async updateMajor(data) {
    const res = await put(`v1/major/${data.id}`, data)
    return res
  }

  static async searchMajors(keyword) {
    const res = await get(`v1/major/search?keyword=${keyword}`)
    return res
  }

  static async getAllMajor() {
    const res = await get('v1/major/all')
    return res
  }

  static async getMajorByPage(keyword, page = 0, count = 10) {
    let res = null
    if (keyword !== '') {
      res = await get('v1/major/page', { page, count, keyword })
    } else {
      res = await get('v1/major/page', { page, count })
    }
    return res
  }
}

export default Major
