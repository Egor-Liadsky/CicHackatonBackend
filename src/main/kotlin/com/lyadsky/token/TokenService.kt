package com.lyadsky.token

import com.lyadsky.token.TokenClaim
import com.lyadsky.token.TokenConfig


interface TokenService {
    fun generateJWTToken(config: TokenConfig, vararg claims: TokenClaim): String
}