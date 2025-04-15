package org.example.services;

import org.example.models.KeHoachDayHoc;
import org.example.models.HocPhan;
import org.example.repositories.KeHoachDayHocRepository;
import org.example.repositories.HocPhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KeHoachDayHocService {

    @Autowired
    private KeHoachDayHocRepository keHoachDayHocRepository;

    @Autowired
    private HocPhanRepository hocPhanRepository;

    @Transactional
    public KeHoachDayHoc themKeHoach(KeHoachDayHoc keHoachDayHoc) {
        if (keHoachDayHocRepository.existsByIdChuyenNganh(keHoachDayHoc.getIdChuyenNganh())) {
            throw new RuntimeException("Kế hoạch dạy học đã tồn tại cho chuyên ngành này");
        }
        KeHoachDayHoc savedKeHoach = keHoachDayHocRepository.save(keHoachDayHoc);
        return populateHocPhanInfo(savedKeHoach);
    }

    @Transactional
    public KeHoachDayHoc capNhatKeHoach(Integer id, KeHoachDayHoc keHoachDayHoc) {
        KeHoachDayHoc existingKeHoach = keHoachDayHocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kế hoạch dạy học"));
        
        existingKeHoach.setIdChuyenNganh(keHoachDayHoc.getIdChuyenNganh());
        existingKeHoach.setTenChuyenNganh(keHoachDayHoc.getTenChuyenNganh());
        existingKeHoach.setIdHocPhan(keHoachDayHoc.getIdHocPhan());
        existingKeHoach.setHocKyThucHien(keHoachDayHoc.getHocKyThucHien());
        
        KeHoachDayHoc updatedKeHoach = keHoachDayHocRepository.save(existingKeHoach);
        return populateHocPhanInfo(updatedKeHoach);
    }

    @Transactional
    public void xoaKeHoach(Integer id) {
        if (!keHoachDayHocRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy kế hoạch dạy học");
        }
        keHoachDayHocRepository.deleteById(id);
    }

    public List<KeHoachDayHoc> layTatCaKeHoach() {
        List<KeHoachDayHoc> keHoachList = keHoachDayHocRepository.findAll();
        return keHoachList.stream()
                .map(this::populateHocPhanInfo)
                .collect(Collectors.toList());
    }

    private KeHoachDayHoc populateHocPhanInfo(KeHoachDayHoc keHoach) {
        List<KeHoachDayHoc.HocPhanInfo> hocPhanInfos = new ArrayList<>();
        String[] idHocPhans = keHoach.getIdHocPhan().split(",");
        for (String id : idHocPhans) {
            try {
                Integer idHocPhan = Integer.parseInt(id.trim());
                HocPhan hocPhan = hocPhanRepository.findById(idHocPhan).orElse(null);
                if (hocPhan != null) {
                    KeHoachDayHoc.HocPhanInfo info = new KeHoachDayHoc.HocPhanInfo();
                    info.setIdHocPhan(hocPhan.getIdHocPhan());
                    info.setMaHP(hocPhan.getMaHP());
                    info.setTenHP(hocPhan.getTenHP());
                    hocPhanInfos.add(info);
                }
            } catch (NumberFormatException e) {
                // Bỏ qua nếu không phải số
            }
        }
        keHoach.setHocPhans(hocPhanInfos);
        return keHoach;
    }

    public KeHoachDayHoc layKeHoachTheoId(Integer id) {
        KeHoachDayHoc keHoach = keHoachDayHocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kế hoạch dạy học"));
        return populateHocPhanInfo(keHoach);
    }

    public List<KeHoachDayHoc> layKeHoachTheoHocKy(Integer hocKy) {
        List<KeHoachDayHoc> keHoachList = keHoachDayHocRepository.findByHocKyThucHien(hocKy);
        return keHoachList.stream()
                .map(this::populateHocPhanInfo)
                .collect(Collectors.toList());
    }

    public List<KeHoachDayHoc> layKeHoachTheoChuyenNganhVaHocKy(Integer idChuyenNganh, Integer hocKy) {
        List<KeHoachDayHoc> keHoachList = keHoachDayHocRepository.findByIdChuyenNganhAndHocKyThucHien(idChuyenNganh, hocKy);
        return keHoachList.stream()
                .map(this::populateHocPhanInfo)
                .collect(Collectors.toList());
    }

    public List<KeHoachDayHoc> layKeHoachTheoHocPhanVaHocKy(Integer hocPhanId, Integer hocKy) {
        List<KeHoachDayHoc> keHoachList = keHoachDayHocRepository.findAll().stream()
                .filter(keHoach -> {
                    String[] hocPhanIds = keHoach.getIdHocPhan().split(",");
                    for (String id : hocPhanIds) {
                        if (id.trim().equals(String.valueOf(hocPhanId))) {
                            return true;
                        }
                    }
                    return false;
                })
                .filter(keHoach -> keHoach.getHocKyThucHien().equals(hocKy))
                .collect(Collectors.toList());
        return keHoachList.stream()
                .map(this::populateHocPhanInfo)
                .collect(Collectors.toList());
    }

    @Transactional
    public void themHocPhanVaoKeHoach(Integer idKeHoach, Integer idHocPhan) {
        KeHoachDayHoc keHoach = keHoachDayHocRepository.findById(idKeHoach)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kế hoạch dạy học"));

        String currentHocPhans = keHoach.getIdHocPhan();
        if (currentHocPhans.isEmpty()) {
            keHoach.setIdHocPhan(String.valueOf(idHocPhan));
        } else {
            keHoach.setIdHocPhan(currentHocPhans + "," + idHocPhan);
        }

        keHoachDayHocRepository.save(keHoach);
    }

    @Transactional
    public void xoaHocPhanKhoiKeHoach(Integer idKeHoach, Integer idHocPhan) {
        KeHoachDayHoc keHoach = keHoachDayHocRepository.findById(idKeHoach)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kế hoạch dạy học"));

        String[] hocPhanIds = keHoach.getIdHocPhan().split(",");
        String newHocPhans = java.util.Arrays.stream(hocPhanIds)
                .filter(id -> !id.trim().equals(String.valueOf(idHocPhan)))
                .collect(Collectors.joining(","));

        keHoach.setIdHocPhan(newHocPhans);
        keHoachDayHocRepository.save(keHoach);
    }
}