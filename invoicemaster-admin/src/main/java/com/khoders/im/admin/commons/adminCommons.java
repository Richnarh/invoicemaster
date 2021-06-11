/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.commons;

import com.khoders.resource.enums.AccessLevel;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "adminCommons")
@SessionScoped
public class adminCommons implements Serializable
{
    public List<AccessLevel> getAccessLevelList()
    {
        return Arrays.asList(AccessLevel.values());
    }
}
