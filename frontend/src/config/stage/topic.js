const teamRouter = {
  route: null,
  name: null,
  title: '课题管理',
  type: 'folder', // 类型: folder, tab, view
  icon: 'iconfont icon-keti',
  filePath: 'view/topic/', // 文件路径
  order: null,
  inNav: true,
  children: [
    // {
    //   title: '课题任务',
    //   type: 'view',
    //   route: '/topic/topic-task',
    //   filePath: 'view/topic/topic-task.vue',
    //   inNav: true,
    //   icon: 'iconfont icon-tushuguanli',
    // },
    {
      title: '课题审核',
      type: 'view',
      route: '/topic/aduit',
      filePath: 'view/topic/topic-aduit.vue',
      inNav: true,
      icon: 'iconfont icon-tushuguanli',
      permission: ['指导教师'],
    },
    {
      title: '课题评分',
      type: 'view',
      route: '/topic/score',
      filePath: 'view/topic/score.vue',
      inNav: true,
      icon: 'iconfont icon-tushuguanli',
      permission: ['指导教师', '答辩小组'],
    },
    {
      title: '查看评分',
      type: 'view',
      route: '/topic/score-result',
      filePath: 'view/topic/score-student.vue',
      inNav: true,
      icon: 'iconfont icon-tushuguanli',
    },
    // {
    //   title: '课题补分',
    //   type: 'view',
    //   route: '/topic/score-sup',
    //   filePath: 'view/topic/score-sup.vue',
    //   inNav: true,
    //   icon: 'iconfont icon-tushuguanli',
    //   permission: ['指导教师', '答辩小组'],
    // },
  ],
}

export default teamRouter
