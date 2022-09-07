CREATE TABLE `tb_diet_group` (
`id` binary(255) not null, 
`ctrl_param_max_val` double precision not null, 
`ctrl_param_min_val` double precision not null,
`ctrl_param_name` varchar(255) not null, 
`description` varchar(255), 
`diet_id` binary(255), 
`name` varchar(255) not null, 
`nutritionist_id` binary(255), 
primary key(id)) 
ENGINE=InnoDB DEFAULT CHARSET=latin1;

