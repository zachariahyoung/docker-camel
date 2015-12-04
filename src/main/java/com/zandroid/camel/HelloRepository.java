package com.zandroid.camel;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Zach on 12/3/2015.
 */
public interface HelloRepository extends JpaRepository<Hello,Integer> {
}
