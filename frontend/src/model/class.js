import { get, post, put, _delete } from '@/lin/plugin/axios'

class Class {
  static async createClass(data) {
    const res = await post('v1/class', data)
    return res
  }

  static async deleteClass(id) {
    const res = await _delete(`v1/class/${id}`)
    return res
  }

  static async getClass(id) {
    const res = await get(`v1/class/${id}`)
    return res
  }

  static async updateClass(data) {
    const res = await put(`v1/class/${data.id}`, data)
    return res
  }

  static async searchClass(keyword) {
    const res = await get(`v1/class/search?keyword=${keyword}`)
    return res
  }

  static async getAllClass() {
    const res = await get('v1/class/all')
    return res
  }

  static async getClassByPage(keyword, page = 0, count = 10) {
    let res = null
    if (keyword !== '') {
      res = await get('v1/class/page', { page, count, keyword })
    } else {
      res = await get('v1/class/page', { page, count })
    }
    return res
  }
}

export default Class
