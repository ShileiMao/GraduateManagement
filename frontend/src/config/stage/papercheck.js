const paperCheckRouter = {
    route: null,
    name: null,
    title: '论文管理',
    type: 'folder', // 类型: folder, tab, view
    icon: 'iconfont icon-shuju',
    filePath: 'view/papercheck/', // 文件路径
    order: null,
    inNav: true,
    children: [
      {
        title: '论文接收',
        type: 'view',
        route: '/essay/essay-receive',
        filePath: 'view/essay/essay-receive.vue',
        inNav: true,
        icon: 'iconfont icon-tushuguanli',
        permission: ['指导教师', '发布选题', '答辩小组'],
      },
      {
        title: '论文上传',
        type: 'view',
        route: '/essay/essay-upload',
        filePath: 'view/essay/essay-view.vue',
        inNav: true,
        icon: 'iconfont icon-tushuguanli',
        permission: ['论文选题'],
      },
      {
        title: '查重',
        type: 'view',
        route: '/papercheck/papercheck',
        filePath: 'view/papercheck/papercheck.vue',
        inNav: true,
        icon: 'iconfont icon-tushuguanli',
        permission: ['论文上传下载'],
      },
    ],
  }
  
  export default paperCheckRouter
  