package io.github.xmchxup.latticy.common.listener;

/**
 * @author pedro@TaleLin
 * @author colorful@TaleLin
 */
//@Component
//public class PermissionHandleListener implements ApplicationListener<ContextRefreshedEvent> {
//
//    @Autowired
//    private PermissionService permissionService;
//
//    @Autowired
//    private PermissionMetaCollector metaCollector;
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        addNewPermissions();
//        removeUnusedPermissions();
//    }
//
//    private void addNewPermissions() {
//        metaCollector.getMetaMap().values().forEach(meta -> {
//            String module = meta.getModule();
//            String permission = meta.getPermission();
//            createPermissionIfNotExist(permission, module);
//        });
//    }
//
//    private void removeUnusedPermissions() {
//        List<PermissionDO> allPermissions = permissionService.list();
//        Map<String, MetaInfo> metaMap = metaCollector.getMetaMap();
//        for (PermissionDO permission : allPermissions) {
//            boolean stayedInMeta = metaMap
//                    .values()
//                    .stream()
//                    .anyMatch(meta -> meta.getModule().equals(permission.getModule())
//                            && meta.getPermission().equals(permission.getName()));
//            if (!stayedInMeta) {
//                permission.setMount(false);
//                permissionService.updateById(permission);
//            }
//        }
//    }
//
//    private void createPermissionIfNotExist(String name, String module) {
//        QueryWrapper<PermissionDO> wrapper = new QueryWrapper<>();
//        wrapper.lambda().eq(PermissionDO::getName, name).eq(PermissionDO::getModule, module);
//        PermissionDO permission = permissionService.getOne(wrapper);
//        if (null ==permission) {
//            permissionService.save(PermissionDO.builder().module(module).name(name).build());
//        }
//
//        if (null != permission && null ==permission.getMount()) {
//            permission.setMount(true);
//            permissionService.updateById(permission);
//        }
//    }
//}
