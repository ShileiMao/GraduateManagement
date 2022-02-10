import adminConfig from './admin'
// import bookConfig from './book' // 引入图书管理路由文件
import taskConfig from './task'
import papercheck from './papercheck'
import systemConfig from './system-data'
import teamConfig from './team'
import scorecardConfig from './scorecard'
import topicConfig from './topic'
// import pluginsConfig from './plugin'
import Utils from '@/lin/util/util'

// eslint-disable-next-line import/no-mutable-exports
let homeRouter = [
  {
    title: '论文管理系统',
    type: 'view',
    name: Symbol('about'),
    route: '/about',
    filePath: 'view/about/about.vue',
    inNav: true,
    icon: 'iconfont icon-iconset0103',
    order: 1,
  },
  {
    title: '启动论文',
    type: 'view',
    name: Symbol('start'),
    route: '/start',
    filePath: 'view/start/index.vue',
    inNav: true,
    icon: 'iconfont icon-qidong',
    order: 0,
    permission: ['启动论文'],
  },
  {
    title: '论文信息',
    type: 'view',
    name: Symbol('graduate-info'),
    route: '/graduate-info',
    filePath: 'view/graduate-info/index.vue',
    inNav: true,
    icon: 'iconfont icon-xinxi-',
    order: 4,
  },
  // {
  //   title: '统计信息',
  //   type: 'view',
  //   name: Symbol('statistics'),
  //   route: '/statistics',
  //   filePath: 'view/statistics/index.vue',
  //   inNav: true,
  //   icon: 'iconfont icon-statics',
  //   order: 5,
  // },
  {
    title: '日志管理',
    type: 'view',
    name: Symbol('log'),
    route: '/log',
    filePath: 'view/log/log.vue',
    inNav: true,
    icon: 'iconfont icon-rizhi',
    order: 3,
    permission: ['查询所有日志'],
  },
  {
    title: '个人中心',
    type: 'view',
    name: Symbol('center'),
    route: '/center',
    filePath: 'view/center/center.vue',
    inNav: false,
    icon: 'iconfont icon-rizhiguanli',
  },
  {
    title: '404',
    type: 'view',
    name: Symbol('404'),
    route: '/404',
    filePath: 'view/error-page/404.vue',
    inNav: false,
    icon: 'iconfont icon-rizhiguanli',
  },
  taskConfig,
  // bookConfig,
  papercheck,
  adminConfig,
  systemConfig,
  teamConfig,
  topicConfig,
  scorecardConfig,
]

// const plugins = [...pluginsConfig]
const plugins = []

// 筛除已经被添加的插件
function filterPlugin(data) {
  if (plugins.length === 0) {
    return
  }
  if (Array.isArray(data)) {
    data.forEach(item => {
      filterPlugin(item)
    })
  } else {
    const findResult = plugins.findIndex(item => data === item)
    if (findResult >= 0) {
      plugins.splice(findResult, 1)
    }
    if (data.children) {
      filterPlugin(data.children)
    }
  }
}

filterPlugin(homeRouter)

homeRouter = homeRouter.concat(plugins)

// 处理顺序
homeRouter = Utils.sortByOrder(homeRouter)

// 使用 Symbol 处理 name 字段, 保证唯一性
const deepReduceName = target => {
  if (Array.isArray(target)) {
    target.forEach(item => {
      if (typeof item !== 'object') {
        return
      }
      deepReduceName(item)
    })
    return
  }
  if (typeof target === 'object') {
    if (typeof target.name !== 'symbol') {
      // eslint-disable-next-line no-param-reassign
      target.name = target.name || Utils.getRandomStr()
      // eslint-disable-next-line no-param-reassign
      target.name = Symbol(target.name)
    }

    if (Array.isArray(target.children)) {
      target.children.forEach(item => {
        if (typeof item !== 'object') {
          return
        }
        deepReduceName(item)
      })
    }
  }
}

deepReduceName(homeRouter)

export default homeRouter
