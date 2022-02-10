const taskRouter = {
    route: null,
    name: null,
    title: '论文选题',
    type: 'folder', // 类型: folder, tab, view
    icon: 'iconfont icon-keti',
    filePath: 'view/task/', // 文件路径
    order: null,
    inNav: true,
    children: [
      {
        title: '发布选题',
        type: 'view',
        route: '/topic/task',
        filePath: 'view/task/task-list.vue',
        inNav: true,
        icon: 'iconfont icon-tushuguanli',
        permission: ['发布选题'],
      },
      {
        title: '选题',
        type: 'view',
        route: '/topic/task-assign',
        filePath: 'view/task/task-assign.vue',
        inNav: true,
        icon: 'iconfont icon-tushuguanli',
        permission: ['论文选题'],
      },
    ],
  }
  
  export default taskRouter
  