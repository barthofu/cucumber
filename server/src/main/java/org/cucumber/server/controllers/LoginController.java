package org.cucumber.server.controllers;

import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.generics.Status;
import org.cucumber.common.dto.requests.LoginRequest;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.common.utils.Routes;
import org.cucumber.server.core.SocketManager;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.services.AuthService;
import org.cucumber.server.utils.classes.Controller;
import org.cucumber.common.utils.Timeout;
import org.cucumber.server.utils.errors.InvalidCredentialsException;
import org.cucumber.server.utils.errors.UserAlreadyLoggedInException;
import org.cucumber.server.utils.mappers.UserMapper;
import org.mapstruct.factory.Mappers;

