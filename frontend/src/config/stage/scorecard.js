const scorecardRouter = {
  route: null,
  name: null,
  title: '评分管理',
  type: 'folder', // 类型: folder, tab, view
  icon: 'iconfont icon-shuju',
  filePath: 'view/score/', // 文件路径
  order: null,
  inNav: true,
  children: [
    {
      title: '评分准则',
      type: 'view',
      route: '/score/question',
      filePath: 'view/score/question.vue',
      inNav: true,
      icon: 'iconfont icon-tushuguanli',
      permission: ['指导教师'],
    },
    {
      title: '评分表模板',
      type: 'view',
      route: '/score/card',
      filePath: 'view/score/card-list.vue',
      inNav: true,
      icon: 'iconfont icon-tushuguanli',
      permission: ['指导教师'],
    },
    {
      title: '评分表',
      type: 'view',
      route: '/score/scorecard',
      filePath: 'view/score/scorecard.vue',
      inNav: true,
      icon: 'iconfont icon-tushuguanli',
      permission: ['指导教师', '答辩小组'],
    },
    
  ],
}

export default scorecardRouter
