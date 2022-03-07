import { get, post, put, _delete } from '@/lin/plugin/axios'

class Card {
  static async createCard(data) {
    const res = await post('v1/card', data)
    return res
  }

  static async deleteCard(id) {
    const res = await _delete(`v1/card/${id}`)
    return res
  }

  static async getCard(id) {
    const res = await get(`v1/card/${id}`)
    return res
  }

  static async updateCard(id, data) {
    const res = await put(`v1/card/${id}`, data)
    return res
  }

  static async getAllCardWithOptions() {
    console.log("get all card with options")
    const res = await get('v1/card/options')
    return res
  }

  static async getCardByPage(page = 0, count = 10) {
    const res = await get('v1/card/page', { page, count })
    return res
  }
}

export default Card
