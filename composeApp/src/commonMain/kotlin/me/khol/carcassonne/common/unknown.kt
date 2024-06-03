package me.khol.carcassonne.common


//@Composable
//fun a() {
//    Row(
//        Modifier.align(Alignment.Center)
//    ) {
//        repeat(3) { x ->
//            Column(
//                Modifier.zIndexOnHover()
//            ) {
//                repeat(3) { y ->
//                    if (x == 1 && y == 1) {
//                        val infiniteTransition = rememberInfiniteTransition()
//                        val rotation by infiniteTransition.animateFloat(
//                            initialValue = 0f,
//                            targetValue = 360f,
//                            animationSpec = infiniteRepeatable(
//                                animation = tween(10000, easing = LinearEasing),
//                                repeatMode = RepeatMode.Restart
//                            )
//                        )
//                        val color = MaterialTheme.colors.surface
////                        val shape = RectangleShape
//                        val shape = RoundedCornerShape(4.dp)
//                        val elevation = 16.dp
//
//                        Box(
//                            Modifier
//                                .rotate(rotation)
//                                .shadow(
//                                    elevation, shape, clip = true
//                                )
//                                .size(128.dp, 128.dp)
//                                .padding(2.dp)
//                                .zIndex(3f)
//                                .background(
//                                    color = color,
//                                    shape = shape
//                                )
//                                .clip(shape),
//                        )
////                        Surface(
////                            modifier = Modifier
////                                .rotate(rotation)
////                                .zIndex(3f)
////                                .size(128.dp, 128.dp)
////                                .padding(2.dp),
////                            elevation = elevation,
////                            shape = shape,
////                        ) {}
//
//                    } else {
//                        Tile(
//                            Modifier
//                                .zIndexOnHover()
//                        )
//                    }
//                }
//            }
//        }
//    }
//}