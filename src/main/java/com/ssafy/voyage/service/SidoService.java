package com.ssafy.voyage.service;

import com.ssafy.voyage.dto.response.SidoDto;
import com.ssafy.voyage.repository.SidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SidoService {
    private final SidoRepository sidoRepository;

    public List<SidoDto> findAll() {
        return sidoRepository.findAll().stream()
            .map(sido -> sido.toDto())
            .collect(Collectors.toList());
    }
}
