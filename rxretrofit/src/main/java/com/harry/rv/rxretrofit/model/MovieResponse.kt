/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.rxretrofit.model

import org.jetbrains.annotations.NotNull
import java.io.Serializable

/**
 * 类/接口描述

 * @author Harry
 */
 class MovieResponse : Serializable {
    var id: String? = null
    var alt: String? = null
    var year: String? = null
    var title: String? = null
    var original_title: String? = null
    @NotNull
    var genres: List<String>? = null
    var casts: List<Cast>? = null
    var directors: List<Cast>? = null
    @NotNull
    var images: Avatars? = null

    inner class Cast : Serializable {
        var id: String? = null
        var name: String? = null
        var alt: String? = null
        var avatars: Avatars? = null
    }

   inner class Avatars : Serializable {
        var small: String? = null
        var medium: String? = null
        @NotNull
        var large: String? = null
    }

    override fun equals(o: Any?): Boolean {
        return this.id == (o as MovieResponse).id
    }
}
