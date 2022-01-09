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
        title: '查重',
        type: 'view',
        route: '/papercheck/papercheck',
        filePath: 'view/papercheck/papercheck.vue',
        inNav: true,
        icon: 'iconfont icon-tushuguanli',
        permission: ['指导教师', '答辩小组'],
      },
    ],
  }
  
  export default paperCheckRouter
  